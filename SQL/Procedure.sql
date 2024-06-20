DELIMITER //

CREATE PROCEDURE find_criteria(
    IN str_condition JSON, 
    IN page_size INT,
    IN page_number INT
)
BEGIN 
    DECLARE start_index INT;
    DECLARE name_st VARCHAR(100);
    DECLARE birth_date_start DATE;
    DECLARE birth_date_end DATE;
    DECLARE number_phone VARCHAR(100);
    DECLARE address VARCHAR(100);
    DECLARE email VARCHAR(100);
    DECLARE description_st VARCHAR(100);
    DECLARE gpa FLOAT;
    DECLARE class_name VARCHAR(100);
    DECLARE str_new VARCHAR(1000);


    SET name_st = JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.name'));
    SET number_phone = JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.numberPhone'));
    SET address = JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.address'));
    SET email = JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.email'));
    SET description_st = JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.description'));
    SET gpa = CAST(JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.gpa')) AS DECIMAL(3, 2));
	 SET class_name = IF(JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.className')) IS NOT NULL 
                    AND JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.className')) != 'null', 
                    JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.className')),
                    NULL);


    
    IF JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateStart')) IS NOT NULL 
       AND JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateStart')) != 'null' 
       AND JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateStart')) != '' THEN
        SET birth_date_start = STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateStart')), '%Y-%m-%d');
    ELSE
        SET birth_date_start = NULL;
    END IF;

    IF JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateEnd')) IS NOT NULL 
       AND JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateEnd')) != 'null' 
       AND JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateEnd')) != '' THEN
        SET birth_date_end = STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(str_condition, '$.birthDateEnd')), '%Y-%m-%d');
    ELSE
        SET birth_date_end = NULL;
    END IF;

  
    SET start_index = (page_number - 1) * page_size;

  
    SET str_new = '';

    
    IF name_st IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND name LIKE "%', name_st, '%"');
    END IF;

    IF birth_date_start IS NOT NULL THEN
        IF birth_date_end IS NOT NULL THEN
            SET str_new = CONCAT(str_new, ' AND birth_date BETWEEN "', DATE_FORMAT(birth_date_start, '%Y-%m-%d'), '" AND "', DATE_FORMAT(birth_date_end, '%Y-%m-%d'), '"');
        ELSE
            SET str_new = CONCAT(str_new, ' AND birth_date >= "', DATE_FORMAT(birth_date_start, '%Y-%m-%d'), '"');
        END IF;
    END IF;

    IF number_phone IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND number_phone LIKE "%', number_phone, '%"');
    END IF;

    IF address IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND address LIKE "%', address, '%"');
    END IF;

    IF email IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND email LIKE "%', email, '%"');
    END IF;

    IF description_st IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND description LIKE "%', description_st, '%"');
    END IF;

    IF gpa IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND gpa >= ', gpa);
    END IF;

    IF class_name IS NOT NULL THEN
        SET str_new = CONCAT(str_new, ' AND classroom_id IN (SELECT id FROM tbl_class WHERE class_name LIKE "%', class_name, '%")');
    END IF;

    
    SET @sql_query = CONCAT('SELECT * FROM tbl_student WHERE 1=1', str_new, ' LIMIT ', start_index, ',', page_size);

  
    PREPARE stmt FROM @sql_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

END //

DELIMITER ;

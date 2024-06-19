DELIMITER $$

CREATE PROCEDURE find_criteria(
    IN str_condition VARCHAR(1000), 
    IN page_size INT,
    IN page_number INT
)
BEGIN 
    DECLARE start_index INT;
    
    -- Tính toán vị trí bắt đầu của phân trang
    SET start_index = (page_number - 1) * page_size;
    
    -- Tạo câu lệnh SQL động để lấy dữ liệu phân trang
    SET @query_data = CONCAT('SELECT * FROM tbl_student WHERE 1 = 1 ', str_condition, 'ORDER BY id DESC LIMIT ',start_index,',',page_size);
    PREPARE stmt_data FROM @query_data;
    EXECUTE stmt_data;
    DEALLOCATE PREPARE stmt_data;

END$$

DELIMITER ;

export interface ClassRoom {
    id: string; // UUID sẽ là một chuỗi trong TypeScript khi gửi đi và nhận về
    className: string;
    email: string;
    establishmentDate: Date | null; //
  }
  
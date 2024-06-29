import { ClassRoom } from "./ClassRoom";

export interface Student {
     id?: string;
     name?: string;
     email?: string;
     description?: string;
     age?: number;
     address?: string;
     avatar?: File;
     classroom?: string;
}
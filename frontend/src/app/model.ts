export interface User {
    id: number;
    firstName: string;
    lastName: string;
    username: string;
    permissions: [string];
  }

export interface ErrorMessage {
    id: number;
    date: string;
    machineId: number;
    operation: string;
    message: string;
  }

export interface Machine {
    id: number;
    machineName: string;
    status: string;
    active: string;
    createdAt: string;
    createdBy: 
      {
        id: number;
        firstName: string;
        lastName: string;
        username: string;
        permissions: [string];
      }
   
}

export interface MachineS{
  machineName: string;
  status: string[];
  createdAfter: string;
  createdBefore: string;
}  

export interface MachineSchedule{
  machineName: string;
  date: string;
  operation: string;
}

export interface MachineC {
  machineName: string;
}

export interface Token {
    jwt: string;
    user: User;
  }


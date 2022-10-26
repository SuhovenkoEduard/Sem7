export enum IActionTypes {
  readAll = 'readAll',
  create = 'create',
  update = 'update',
  findById = 'findById',
  findByName = 'findByName',
  findByAge = 'findByAge',
  deleteById = 'deleteById',
}

export interface IUserData {
  name: string;
  age: number;
}

export interface IUser extends IUserData {
  id: number;
}

export interface IAction {
  type: IActionTypes;
  data?: IUserData | { age: number } | { name: string } | IUser | { id: number };
}


import { IActionTypes, IUser } from '../interfaces'

export interface IRepository {
  [IActionTypes.readAll]: () => Promise<IUser[]>;
  [IActionTypes.create]: (name: string, age: number) => Promise<string>;
  [IActionTypes.update]: (newUser: IUser) => Promise<string>;
  [IActionTypes.findById]: (id: number) => Promise<IUser | null>;
  [IActionTypes.findByName]: (name: string) => Promise<IUser[]>;
  [IActionTypes.findByAge]: (age: number) => Promise<IUser[]>;
  [IActionTypes.deleteById]: (id: number) => Promise<string>;
}

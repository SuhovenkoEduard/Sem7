import { IAction, IActionTypes } from './interfaces'

export const clientActions: IAction[] = [
  {
    // READ ALL
    type: IActionTypes.readAll,
  },
  {
    // CREATE
    type: IActionTypes.create,
    data: { name: 'Kate', age: 33 },
  },
  {
    // FIND BY AGE
    type: IActionTypes.findByAge,
    data: { age: 12 },
  },
  {
    // FIND BY NAME
    type: IActionTypes.findByName,
    data: { name: 'Kate' },
  },
  {
    // UPDATE
    type: IActionTypes.update,
    data: { id: 5, name: 'Leo', age: 34 },
  },
  {
    // FIND BY ID
    type: IActionTypes.findById,
    data: { id: 5 },
  },
  {
    // DELETE
    type: IActionTypes.deleteById,
    data: { id: 1 },
  },
  {
    // READ ALL
    type: IActionTypes.readAll,
  },
]

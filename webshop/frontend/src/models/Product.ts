import { Category } from "./Category"

export type Product = {
  id: number,
  name: string,
  price: number,
  purchasePrice: number,
  category: Category
}
import { useEffect, useState } from "react"
import { Product } from "../models/Product";

function ManageProducts() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/products", {
      headers: {
        "Authorization": sessionStorage.getItem("token") || ""
      }
    })
      .then(res => res.json())
      .then(json => setProducts(json.content));
  }, []);

  const removeProduct = (productId: number) => {
    fetch(`http://localhost:8080/products/${productId}`, {
      method: "DELETE",
      headers: {
        "Authorization": sessionStorage.getItem("token") || ""
      }
    })
      .then(res => res.json())
      .then(json => setProducts(json));
  }

  // function removeProduct(productId) {

  // }

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Purchase price</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(product => 
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>{product.name}</td>
              <td>{product.price}</td>
              <td>{product.purchasePrice}</td>
              <td>{product.category?.name}</td>
              <td><button onClick={() => removeProduct(product.id)}>x</button></td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}

export default ManageProducts
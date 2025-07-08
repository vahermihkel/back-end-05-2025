// rfce
// react functional export component

import { useEffect, useState } from "react"
import { Product } from "../models/Product";
import { Category } from "../models/Category";

// rafce
// react arrow function export component

function AddProduct() {
  const [product, setProduct] = useState<Product>({id: 0, name: "", price: 0, purchasePrice: 0, category: {id: 0}}); // {"name": "Coca"}
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/categories")
      .then(res => res.json())
      .then(json => setCategories(json));
  }, []);

  const add = () => {
    if (product.name === undefined || product.name === "") {
      alert("Please enter name");
      return;
    }

    if (product.price === undefined || product.price === 0) {
      alert("Please enter price");
      return;
    }

    fetch(`http://localhost:8080/products`, {
      method: "POST", 
      body: JSON.stringify(product), 
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token") || ""
      }})
      .then(res => res.json())
      .then(() => alert("Added!"));
  }

  return (
    <div>
      <div>{JSON.stringify(product)}</div>
      <label>Name</label> <br />
      <input onChange={(e) => setProduct({...product, name: e.target.value})} type="text" /> <br />
      <label>Price</label> <br />
      <input onChange={(e) => setProduct({...product, price: Number(e.target.value)})} type="text" /> <br />
      <label>Purchase price</label> <br />
      <input onChange={(e) => setProduct({...product, purchasePrice: Number(e.target.value)})} type="text" /> <br />
      <label>Category</label> <br />
      {/* <input onChange={(e) => setProduct({...product, category: e.target.value})} type="text" /> <br /> */}
      <select onChange={(e) => setProduct({...product, category: {id: Number(e.target.value)}})}>
        {categories.map(category => 
          <option key={category.id} value={category.id}>{category.name}</option>
        )}
      </select><br />
      <button onClick={add}>Add</button>
    </div>
  )
}

export default AddProduct
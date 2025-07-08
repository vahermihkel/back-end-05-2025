import { useContext, useState } from "react"
import type { Product } from "../models/Product";
import { AuthContext } from "../context/AuthContext";

function Cart() {
  const [cart, setCart] = useState<Product[]>(JSON.parse(localStorage.getItem("cart") || "[]"));
  const {loggedIn} = useContext(AuthContext);

  const empty = () => {
    cart.splice(0);
    setCart([]); // HTMLi uuendada
    localStorage.setItem("cart", "[]"); // LS uuendada
  }

  const removeFromCart = (index: number) => {
    cart.splice(index, 1);
    setCart(cart.slice()); // HTMLi uuendada
    localStorage.setItem("cart", JSON.stringify(cart)); // LS uuendada
  }

  const calculateCartSum = () => {
    let sum = 0;
    cart.forEach(cartProduct => sum += cartProduct.price);
    return sum;
  }

  const pay = () => {
    fetch("http://localhost:8080/payment", {
      method: "POST",
      body: JSON.stringify(cart),
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token") || ""
      }
    }).then(res => res.text())
      .then(json => {
        window.location.href = json;
      })
  }

  return (
    <div>
      {cart.length > 0 && <button onClick={empty}>Tühjenda</button>}
      {cart.map((cartProduct, index) => 
        <div key={index}>
          <div>{cartProduct.name}</div>
          <div>{cartProduct.price}</div>
          <button onClick={() => removeFromCart(index)}>x</button>
        </div>
      )}
      {cart.length > 0 && 
      <div>
        <div>Kokku: {calculateCartSum()} €</div>
        {loggedIn && <button onClick={pay}>Maksa</button>}
      </div>}
    </div>
  )
}

export default Cart
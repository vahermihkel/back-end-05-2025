import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Product } from "../models/Product";

function MainPage() {
  const [count, setCount] = useState(1);
  const [page, setPage] = useState(0);
  const [products, setProducts] = useState<Product[]>([]);
  const {t} = useTranslation();

  useEffect(() => {
    // fetch("http://localhost:8080/public-products?page=" + page + "&size=2")
    fetch(`http://localhost:8080/public-products?page=${page}&size=2`)
      .then(res => res.json())
      .then(json => setProducts(json.content))
  }, [page]);

  const addToCart = (product: Product) => {
    const cartLS = JSON.parse(localStorage.getItem("cart") || "[]") ;
    cartLS.push(product);
    localStorage.setItem("cart", JSON.stringify(cartLS));
  }

  return (
    <div>
      <button disabled={count === 1} onClick={() => setCount(count - 1)}>-</button>
      <span>{count}</span>
      <button onClick={() => setCount(count + 1)}>+</button>

      {products.map(product =>
          <div key={product.id}>
            <div>Nimi: {product.name}</div>
            <div>Hind: {product.price}</div>
            <button onClick={() => addToCart(product)}>Lisa ostukorvi</button>
          </div>
      )}
      <br />
      <button onClick={() => setPage(page-1)}>{t("home.previous")}</button>
      <span>{page}</span>
      <button onClick={() => setPage(page+1)}>{t("home.next")}</button>
    </div>
  )
}

export default MainPage
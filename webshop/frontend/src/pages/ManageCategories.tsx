import { useEffect, useRef, useState } from "react"
import { Category } from "../models/Category";

function ManageCategories() {
  const [categories, setCategories] = useState<Category[]>([]);
  const nameRef = useRef<HTMLInputElement>(null); // lähevad inputi sisse, et neist väärtust välja võtta
  const activeRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    fetch("http://localhost:8080/categories")
      .then(res => res.json())
      .then(json => setCategories(json));
  }, []);

  const removeCategory = (categoryId: number) => {
    fetch(`http://localhost:8080/categories/${categoryId}`, {
        method: "DELETE",
        headers: {
            "Authorization": sessionStorage.getItem("token") || ""
        }
      })
      .then(res => res.json())
      .then(json => setCategories(json));
  }

  const addCategory = () => {
    if (nameRef.current === null || activeRef.current === null) {
      return;
    }

    const category = {
      "name": nameRef.current.value,
      "active": activeRef.current.checked
    }

    fetch(`http://localhost:8080/categories`, {
      method: "POST",
      body: JSON.stringify(category),
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token") || ""
      }
    })
      .then(res => res.json())
      .then(json => setCategories(json));
  }

  return (
    <div>
      <label>Kategooria nimi</label> <br />
      <input ref={nameRef} type="text" /> <br />
      <label>Kategooria aktiivsus</label> <br />
      <input ref={activeRef} type="checkbox" /> <br />
      <button onClick={addCategory}>Lisa</button>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Active</th>
          </tr>
        </thead>
        <tbody>
          {categories.map(category => 
            <tr key={category.id}>
              <td>{category.id}</td>
              <td>{category.name}</td>
              <td>{category.active ? <div>Aktiivne</div> : <div>Mitteaktiivne</div>}</td>
              <td><button onClick={() => removeCategory(category.id)}>x</button></td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}

export default ManageCategories
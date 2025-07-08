import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";

function Profile() {
  const {person, findPersonByToken} = useContext(AuthContext);
  const [personToEdit, setPerson] = useState<any>(person);
  
  const edit = () => {
    fetch("http://localhost:8080/person", {
            method: "PUT",
            body: JSON.stringify(personToEdit),
            headers: {
                "Content-Type": "application/json",
                "Authorization": sessionStorage.getItem("token") || ""
            }
        }).then(res => res.json())
            .then(() => {
                findPersonByToken((sessionStorage.getItem("token") || "").replace("Bearer ", ""));
            })
  }

  return (
    <div>
      <div className="registerForm">
      <label>Name</label>
      <input defaultValue={person.name} onChange={(e) => setPerson({...personToEdit, name: e.target.value})} type="text" placeholder="Enter your name..." />
      <label>Email</label>
      <input defaultValue={person.email} onChange={(e) => setPerson({...personToEdit, email: e.target.value})} type="text" placeholder="Enter your email..." />
      <label>Password</label>
      <input defaultValue={person.password} onChange={(e) => setPerson({...personToEdit, password: e.target.value})} type="password" placeholder="Enter your password..." />
      <button className="registerSubmit" onClick={edit}>Edit account</button>
  </div>
    </div>
  )
}

export default Profile
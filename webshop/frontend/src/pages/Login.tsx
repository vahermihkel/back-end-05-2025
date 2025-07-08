import {Link} from "react-router-dom";
import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthContext.tsx";
 
function Login() {
    const [authCredentials, setAuthCredentials] = useState({});
    const {setLoggedIn, findPersonByToken} = useContext(AuthContext);
 
    const login = () => {
        fetch("http://localhost:8080/login", {
            method: "POST",
            body: JSON.stringify(authCredentials),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json())
            .then(json => {
                if (json.token) {
                    sessionStorage.setItem("token", "Bearer " + json.token);
                    setLoggedIn(true);
                    findPersonByToken(json.token);
                } else {
                    alert("Something went wrong!");
                }
                // console.log(json);
            })
    }
 
    return(
        <>
 
            <div className="login">
                <span className="loginTitle">Login</span>
                <div className="loginForm">
                    <label>Email</label>
                    <input onChange={(e) => setAuthCredentials({...authCredentials, email: e.target.value})} type="text" placeholder="Enter your email..." />
                    <label>Password</label>
                    <input onChange={(e) => setAuthCredentials({...authCredentials, password: e.target.value})} type="password" placeholder="Enter your password..." />
                    <button onClick={login} className="loginSubmit">Log in</button>
                    <button className="loginRegisterSubmit">
                        <Link className="link" to="/register">Sign up?</Link>
                    </button>
                </div>
            </div>
        </>
 
    )
}

export default Login
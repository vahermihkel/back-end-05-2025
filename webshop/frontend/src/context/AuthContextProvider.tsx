import {AuthContext} from "./AuthContext.js";
import {ReactNode, useEffect, useState} from "react";

export const AuthContextProvider = ({ children }: {children: ReactNode}) => {

    const [loggedIn, setLoggedIn] = useState(false);
    const [authChecked, setAuthChecked] = useState(false);
    const [person, setPerson] = useState({}); // siia paneme sisselogides /
    // refreshides isiku. tema käest saame ID (mille abil ordereid leida)
    // ja ka rolli (näita mingeid nuppe / ära näita)

    useEffect(() => {
        const token = (sessionStorage.getItem("token") || "").replace("Bearer ", "");
        if (token !== "") {
            setLoggedIn(true);
            findPersonByToken(token);
        } else {
            setLoggedIn(false);
        }
        setAuthChecked(true);
    }, [])

    function findPersonByToken(token: string) {
      fetch("http://localhost:8080/person/" + token, {
        headers: {
            "Authorization": sessionStorage.getItem("token") || ""
        }
      })
        .then(res => res.json())
        .then(json => setPerson(json));
    }

    const logout = () => {
        setLoggedIn(false);
        sessionStorage.removeItem("token");
        setPerson({});
    }


    return (
        <AuthContext.Provider value={{loggedIn, setLoggedIn, logout, findPersonByToken, person, authChecked}}>
            {children}
        </AuthContext.Provider>
    )
}
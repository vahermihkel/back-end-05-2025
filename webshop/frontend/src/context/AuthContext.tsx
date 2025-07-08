import { createContext } from "react";

export const AuthContext = createContext({
  loggedIn: false, 
  setLoggedIn: (newStatus: boolean) => {console.log(newStatus)}, 
  logout: () => {}, 
  findPersonByToken: (token: string) => {console.log(token)}, 
  person: {}, 
  authChecked: false
});
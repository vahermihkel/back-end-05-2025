import { useContext } from "react"
import { Link } from "react-router-dom"
import { AuthContext } from "../context/AuthContext"
import { useTranslation } from 'react-i18next';

function Menu() {
  const {loggedIn, logout, person} = useContext(AuthContext);
  const { t, i18n } = useTranslation();

  const changeLang = (newLanguage) => {
    i18n.changeLanguage(newLanguage);
    localStorage.setItem("language", newLanguage);
  }

  return (
    <div>
      <button onClick={() => changeLang("et")}>EE</button>
      <button onClick={() => changeLang("en")}>EN</button>
      <div>{JSON.stringify(person)}</div>

      <Link to="/">
        <button>{t("nav.homepage")}</button>
      </Link>

      <Link to="/ostukorv">
        <button>{t("nav.cart")}</button>
      </Link>

      {(person.role === "MANAGER" || person.role === "ADMIN") &&
      <>
        <Link to="/lisa-toode">
          <button>{t("nav.add-product")}</button>
        </Link>
        <Link to="/halda-tooted">
          <button>{t("nav.manage-products")}</button>
        </Link>

        <Link to="/halda-kategooriad">
          <button>{t("nav.manage-categories")}</button>
        </Link>
      </>}

      {loggedIn === false ? <>
        <Link to="/login">
          <button>{t("nav.login")}</button>
        </Link>

        <Link to="/register">
          <button>{t("nav.register")}</button>
        </Link>
      </> :
      <>
        <button onClick={logout}>{t("nav.logout")}</button>
        <Link to="/profile">
          <button>{t("nav.profile")}</button>
        </Link>
      </>
      }
    </div>
  )
}

export default Menu
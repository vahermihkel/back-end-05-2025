import './App.css'
import { Route, Routes } from 'react-router-dom';
import MainPage from './pages/MainPage';
import Cart from './pages/Cart';
import ManageProducts from './pages/ManageProducts';
import AddProduct from './pages/AddProduct';
import NotFound from './pages/NotFound';
import Menu from './components/Menu';
import ManageCategories from './pages/ManageCategories';
import Login from './pages/Login';
import Register from './pages/Register';
import { useContext } from 'react';
import { AuthContext } from './context/AuthContext';
import Profile from './pages/Profile';

function App() {
  const {person} = useContext(AuthContext);

  return (
    <>
      <Menu />

      <Routes>
        <Route path='/' element={ <MainPage /> }  />
        <Route path='/ostukorv' element={ <Cart /> }  />
        <Route path='/profile' element={ <Profile /> }  />
         {(person.role === "MANAGER" || person.role === "ADMIN") &&
          <>
            <Route path='/halda-tooted' element={ <ManageProducts /> }  />
            <Route path='/halda-kategooriad' element={ <ManageCategories /> }  />
            <Route path='/lisa-toode' element={ <AddProduct /> }  />
          </>
        }
        <Route path='/login' element={ <Login /> }  />
        <Route path='/register' element={ <Register /> }  />
        <Route path='/*' element={ <NotFound /> }  />
      </Routes>
    </>  )
}

export default App

import Footer from "./components/Footer";
import Header from "./components/Header";
import { BrowserRouter as Router,Routes,Route } from 'react-router-dom';
import Products from "./components/Products";
import ProductDetails from "./components/ProductDetails";
import UserPanel from "./components/UserPanel";
import Login from "./components/Login";
import React, { useEffect, useState } from 'react'
import axios from 'axios'
  
function App() {
  const[isAuth, setIsAuth] = useState(null);
  async function fetchUserAuth() {
    try{
      const responce = await axios.get('http://localhost:8080/user/checkAuth');
      setIsAuth(responce.data);
    }catch(error){
      console.error(error);
    }
  }
  useEffect(() => {
    fetchUserAuth();
  }, [isAuth]);
  return (
    <Router>
      <Header/>
      <Routes>
        <Route path="/" element={<Products/>}></Route>
        <Route path="/product/:id" element={<ProductDetails/>}></Route>
        <Route path="/user" element={<UserPanel setIsAuth={setIsAuth} isAuth={isAuth}/>}></Route>
        <Route path="/login" element={<Login setIsAuth={setIsAuth} isAuth={isAuth}/>}></Route>
      </Routes>
      <Footer/>
    </Router>
  )
}

export default App;

import Footer from "./components/Footer";
import Header from "./components/Header";
import { BrowserRouter as Router,Routes,Route } from 'react-router-dom';
import Products from "./components/Products";
import ProductDetails from "./components/ProductDetails";
import UserPanel from "./components/UserPanel";
import Login from "./components/Login";

function App() {
  return (
    <Router>
      <Header/>
      <Routes>
        <Route path="/" element={<Products/>}></Route>
        <Route path="/product/:id" element={<ProductDetails/>}></Route>
        <Route path="/user" element={<UserPanel/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
      </Routes>
      <Footer/>
    </Router>
  )
}

export default App;

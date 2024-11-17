import Footer from "./components/Footer";
import Header from "./components/Header";
import { BrowserRouter as Router,Routes,Route } from 'react-router-dom';
import Products from "./components/Products";

function App() {
  return (
    <Router>
      <Header/>
      <Routes>
        <Route path="/" element={<Products/>}></Route>
      </Routes>
      <Footer/>
    </Router>
  )
}

export default App;

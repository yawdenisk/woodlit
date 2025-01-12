import axios from 'axios'
import React, { useEffect, useState } from 'react';
import deleteIcon from '../images/delete.png';
import { Link } from 'react-router-dom';
export default function Cart() {
    const [cart, setCart] = useState([]);
    useEffect(() => {
        axios.get('http://localhost:8080/cart/get', {withCredentials: true})
        .then(responce => {
            setCart(responce.data);
        })
        .catch(error =>
            console.log(error)
        )
    },[])
  return (
    <div className='container'>
        <div className='cart'>
     <ul>
     {cart.map(item =>(
        <li key={item.id}>
            <img src={item.product.image} alt='none image'></img>
            <p>{item.product.name}</p>
            <div className='counters'>
              <button>-</button>
            <p>{item.quantity}</p>
            <button>+</button>
            </div>
            <p>€ {item.product.price}</p>
            <button><img src={deleteIcon}/></button>
        </li>
      ))}
     </ul>
     <div className='shipment'>
      <p>Summary</p>
      <p>Additional fees: </p>
      <p>Price of goods: </p>
      <p>Total price: </p>
      <Link>Payment and delivery</Link>
      <Link>Continue shopping</Link>
    </div>
    </div>
    </div>
  )
}

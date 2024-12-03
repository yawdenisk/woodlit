import React, { useEffect, useState } from 'react'
import axios from 'axios';
export default function Products() {
  const[products, setProducts] = useState([]);
  useEffect(() => {
    axios.get('/product/getAll')
    .then(responce => {
      setProducts(responce.data);
    })
    .catch(error => {
      console.error(error);
    })
  }, []);
  return (
    <>
    <div className='container'>
      <div className='products'>
        <ul>
          {products.map(product => (
            <li key={product.id}>
              <p>{product.name}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
    </>
  )
}

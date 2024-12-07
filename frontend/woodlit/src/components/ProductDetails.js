import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

export default function ProductDetails() {
    const {id} = useParams();
    const [product, setProduct] = useState(null);
    useEffect(() => {
        axios.get(`/product/get/${id}`)
        .then(response => {
            setProduct(response.data);
        })
        .catch(error => {
            console.error(error);
        })
    }, [id]);
    if (!product) {
      return <div>Loading...</div>; 
    }
  return (
    <div>
      <p>{product.name}</p>
    </div>
  )
}

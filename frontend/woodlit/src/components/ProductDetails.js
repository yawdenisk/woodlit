import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import loading from '../images/loading.svg'

export default function ProductDetails() {
    const {id} = useParams();
    const [product, setProduct] = useState(null);
    const [activeTab, setActiveTab] = useState('description');
    useEffect(() => {
        axios.get(`http://localhost:8080/product/get/${id}`)
        .then(response => {
            setProduct(response.data);
        })
        .catch(error => {
            console.error(error);
        })
    }, [id]);
    if (!product) {
      return <div className='loading'><img src={loading} alt='none gif'></img></div>; 
    } 
    const formattedFeatures = product.features.split("\n").map((line, index) => (
      <span key={index}>{line}<br /></span>
    ));
    function handleSubmit(){
      axios.post('http://localhost:8080/cart/add',{
        product,
        quantity: 1
      }, {withCredentials: true})
    }
  return (
    <>
    <div className='container'>
      <div className='productDetails'>
        <div className='view'>
          <img src={product.image}></img>
        </div>
        <div className='information'>
          <h1>{product.name}</h1>
          <p>€ {product.price}</p>
          <button onClick={() => handleSubmit()}>ADD TO CART</button>
            <div className='description'>
              <ul>
                <li onClick={() => setActiveTab('description')} style={{borderBottom : activeTab === 'description' ? '1px solid black' : 'none'}}>DESCRIPTION</li>
                <li onClick={() => setActiveTab('features')} style={{borderBottom : activeTab === 'features' ? '1px solid black' : 'none'}}>FEATURES</li>
                <li onClick={() => setActiveTab('construction')} style={{borderBottom : activeTab === 'construction' ? '1px solid black' : 'none'}}>ALL-CEDAR CONSTRUCTION</li>
              </ul>
              {activeTab === 'description' && (
                <p>{product.description}</p>
              )}
              {activeTab === 'features' && (
                <p>{formattedFeatures}</p>
              )}
              {activeTab === 'construction' && (
                <p>The Sunnydale is made from 100% cedar. With small, tight knot structure, your lumber will be less likely to develop small cracks emanating from knots. In laboratory testing, our durable cedar wood proved to be rot resistant and highly resistant to natural decay. All lumber is pre-stained for a smooth and clear appearance, as well as cut and stamped with the part number to help speed up the building process. Some pilot-hole drilling may be required. </p>
              )}
            </div>
        </div>
      </div>
    </div>
    </>
  )
}

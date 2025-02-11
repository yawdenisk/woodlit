import axios from 'axios';
import React, {useEffect, useState} from 'react'
import editIcon from '../images/edit.png'

export default function AdminPanel() {
    const [item, setItem] = useState('products');
    const [products, setProducts] = useState([]);
    const [currentProduct, setCurrentProduct] = useState(null);
    useEffect(() => {
        axios.get('http://localhost:8081/product/getAll')
            .then(responce => {
                setProducts(responce.data);
            })
            .catch(error => {
                console.error(error);
            })
    }, []);

    function handleInputChange(e) {
        const { name, value } = e.target;
        setCurrentProduct({
          ...currentProduct,
          [name]: value,
        });
      }

    function updateProduct(id) {
        const currentProduct = products.find(p => p.id == id)
        setCurrentProduct(currentProduct);
        setItem("updateProduct")
    }

    function handleUpdateProduct(e) {
        e.preventDefault();
        axios.put(`http://localhost:8081/product/update/${currentProduct.id}`, {currentProduct}, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("access_tocken")}`
            }
        })
    }

    return (
        <>
            <div className='container'>
                <div className='adminPanel'>
                    <div className='menu'>
                        <ul>
                            <li>Orders</li>
                            <li onClick={() => setItem("products")}>Products</li>
                            <li>Products</li>
                        </ul>
                    </div>
                    <div className='view'>
                        <ul>
                            {item == "products" && (
                                products.map(product =>
                                    <li key={product.id}>
                                        <img src={product.image}/>
                                        <p>{product.name}</p>
                                        <h4 onClick={() => updateProduct(product.id)}>Edit <img src={editIcon}/></h4>
                                    </li>
                                )
                            )}
                            {item == "updateProduct" && (
                                <div className='updateProduct'>
                                    <form onSubmit={handleUpdateProduct}>
                                        <label>Image</label>
                                        <input type='file'/>
                                        <label>Name</label>
                                        <input type="text" name="name" defaultValue={currentProduct.name}
                                               onChange={handleInputChange}/>
                                        <label>Price</label>
                                        <input type="number" name="price" defaultValue={currentProduct.price}
                                               onChange={handleInputChange}/>
                                        <label>Description</label>
                                        <textarea name="description" defaultValue={currentProduct.description}
                                                  onChange={handleInputChange}/>
                                        <label>Features</label>
                                        <textarea name="features" defaultValue={currentProduct.features}
                                                  onChange={handleInputChange}/>
                                        <button type='submit'>Apply changes</button>
                                    </form>
                                    <img src={currentProduct.image}/>
                                </div>
                            )}
                        </ul>
                    </div>
                </div>
            </div>
        </>
    )
}

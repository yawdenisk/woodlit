
import React, {useEffect, useState} from 'react';
import deleteIcon from '../images/delete.png';
import emptyIcon from '../images/empty.png';
import {Link} from 'react-router-dom';

export default function Cart({cart, setCart}) {
    if (cart.length == 0) {
        return (
            <div className='cartEmpty'>
                <p>Cart is empty</p>
                <img src={emptyIcon}></img>
            </div>
        )
    }
    function increaseQuantity(id){
        setCart(
            cart.map(item =>
                item.id == id ? {...item, quantity: item.quantity + 1} : item
            )
        )
    }
    function decreaseQuantity(id){
        setCart(
            cart.map(item =>
                item.id == id && item.quantity > 1 ? {...item, quantity: item.quantity - 1} : item
            )
        )
    }
    function deleteItem(id){
        setCart(
            cart.filter(item => 
                item.id != id
            )
        )
    }
    return (
        <div className='container'>
            <div className='cart'>
                <ul>
                    {cart.map(item => (
                        <li key={item.id}>
                            <img src={item.image} alt='none image'></img>
                            <p>{item.name} <br></br>  
                            <input type="checkbox" name="instalation" value="true"/> 
                            Instalation + €{item.instalation_price}   
                            </p>
                            <div className='counters'>
                                <button onClick={() => decreaseQuantity(item.id)}>-</button>
                                <p>{item.quantity}</p>
                                <button onClick={() => increaseQuantity(item.id)}>+</button>
                            </div>
                            <p>€ {item.price * item.quantity}</p>
                            <button onClick={() => deleteItem(item.id)}><img src={deleteIcon}/></button>
                        </li>
                    ))}
                </ul>
                <div className='shipment'>
                    <p>Summary</p>
                    <p>Additional fees: </p>
                    <p>Price of goods: € {cart.reduce((total, item) => total + item.price * item.quantity, 0)}</p>
                    <p>Total price: € {cart.reduce((total, item) => total + item.price * item.quantity, 0)}</p>
                    <Link>Payment and delivery</Link>
                    <Link>Continue shopping</Link>
                </div>
            </div>
        </div>
    )
}

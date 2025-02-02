import axios from 'axios'
import React, { useEffect, useState } from 'react'
import loading from '../images/loading.svg'
import Login from './Login';
import { useNavigate } from 'react-router-dom';
export default function UserPanel() {
    const[userDetails, setUserDetails] = useState({}); 
    const[item, setItem] = useState('account');
    const navigate = useNavigate();
    async function fetchUserDetails() {
            try{
                const responce = await axios.get('http://localhost:8081/user/getUserDetails',{headers:{
                    'Authorization': `Bearer ${localStorage.getItem("access_tocken")}`
                }})
                setUserDetails(responce.data);
            }catch(error){  
                navigate('/login')
            }
    }
    useEffect(() => {
        fetchUserDetails();
      }, []);  
    if(!userDetails){
        return (
        <div className='loading'><img src={loading} alt='none gif'></img></div>
        )
    }
    async function logout(){
        try{
            await axios.post('http://localhost:8080/user/logout',{}, {withCredentials:true})
            navigate('/')
        }catch(error){
            console.error(error);
        }
    }
  return (
    <>
        <div className='container'>
            <div className='panel'>
                <div className='menu'>
                    <ul>
                        <li>Account</li>
                        <li>Orders</li>
                        <li onClick={logout}>Logout</li>
                    </ul>
                </div>
                <div className='view'>
                    {item == 'account' &&(
                        <>
                        <p>Email: {userDetails.email}</p>
                        <p>Name: {userDetails.name}</p>
                        <p>Last name: {userDetails.last_name}</p>
                        <p>Country: {userDetails.country}</p>
                        <p>City: {userDetails.city}</p>
                        <p>Address: {userDetails.address}</p>
                        <p>Post index: {userDetails.post_index}</p>
                        </>
                    )}
                </div>
            </div>
        </div>
    </>
  )
}

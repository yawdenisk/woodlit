import axios from 'axios'
import React, { useEffect, useState } from 'react'
import loading from '../images/loading.svg'
import Login from './Login';
import { useNavigate } from 'react-router-dom';
export default function UserPanel({isAuth, setIsAuth}) {
    const[userDetails, setUserDetails] = useState({}); 
    const navigate = useNavigate();
    async function fetchUserDetails() {
        if(isAuth == true){
            try{
                const responce = await axios.get('http://localhost:8080/user/getUserDetails', {withCredentials: true});
                setUserDetails(responce.data);
            }catch(error){
                console.error(error);
            }
        }else{
            navigate('/login')
        }
    }
    useEffect(() => {
            fetchUserDetails();
    }, []);   
  return (
    <>
        <p>{userDetails.name}</p>
    </>
  )
}

import axios from 'axios'
import React, { useEffect, useState } from 'react'
import loading from '../images/loading.svg'
import Login from './Login';
export default function UserPanel() {
    const[userDetails, setUserDetails] = useState(null);
    const[isAuth, setIsAuth] = useState(null);
    useEffect(() => {
        axios.get('http://localhost:8080/user/checkAuth')
        .then(responce => {
            setIsAuth(responce.data);
        })
        .catch(error => {
            console.log(error);
        })
    }, [])
    useEffect(() => {
        if(isAuth == true){
            axios.get('http://localhost:8080/user/getUserDetails')
        .then(responce => {
            setUserDetails(responce.data);
        })
        .catch(error => {
            console.error(error);
    })
        }
    }, [isAuth])
    if (isAuth == false) { 
        return <Login setIsAuth={setIsAuth}  isAuth={isAuth}/>;
    }
    if (isAuth === null || userDetails === null) {  
        return (
            <div className='loading'>
                <img src={loading} alt='none gif' />
            </div>
        );
    }
    
  return (
    <div>
      <p>{userDetails.name}</p>
    </div>
  )
}

  import axios from 'axios'
  import React, { useEffect, useState } from 'react'
  import UserPanel from './UserPanel';  
import {useNavigate } from 'react-router-dom';

  export default function Login({setIsAuth, isAuth}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    // const [csrf, setCsrf] = useState(null);
    // useEffect(() => {
    //   axios.get("http://localhost:8080/user/getCsrf")
    //   .then(responce => {
    //     setCsrf(responce.data);
    //   })
    //   .catch(error =>{
    //     console.error(error);
    // })
    // }, []);
    async function handleSubmit(event){ 
      event.preventDefault();
      try{
        await axios.post("http://localhost:8080/user/login",{  
          username,
          password,
          // _csrf: csrf,  
        },{
            withCredentials:true
        });
        setIsAuth(true);
        navigate("/user")
    }catch(error){
      console.error(error);
    }};
    return (
      <>
      <form className='formLogin'>
        <p>Sing in</p>
        <input type='text' name='username' placeholder='username' onChange={(e) => setUsername(e.target.value)}></input>
        <input type='text' name='password' placeholder='password' onChange={(e) => setPassword(e.target.value)}></input>
        <button onClick={handleSubmit}>Submit</button>
      </form>
      </>
    )
  }

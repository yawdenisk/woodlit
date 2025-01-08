  import axios from 'axios'
  import React, { useEffect, useState } from 'react'
import {Link, useNavigate } from 'react-router-dom';

  export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    
    const navigate = useNavigate();

    async function handleSubmit(event){ 
      event.preventDefault();
      try{
        await axios.post("http://localhost:8080/user/login",{  
          username,
          password,
        },
          {withCredentials:true}  
        );
        navigate("/user")
    }catch(error){
      console.error(error);
    }};
    return (
      <>
      <form className='formLogin' onSubmit={handleSubmit}>
        <p>Sing in</p>
        <input type='text' name='username' placeholder='username' onChange={(e) => setUsername(e.target.value)}></input>
        <input type='text' name='password' placeholder='password' onChange={(e) => setPassword(e.target.value)}></input>
        <button type='submit'>Submit</button>
        <h6>Don't have an account? <Link to="/register">Sign up</Link></h6>
      </form>
      </>
    )
  }

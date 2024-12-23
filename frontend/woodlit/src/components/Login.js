import axios from 'axios'
import React, { useEffect, useState } from 'react'
import UserPanel from './UserPanel';

export default function Login({setIsAuth, isAuth}) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [csrf, setCsrf] = useState(null);
  useEffect(() => {
    axios.get("http://localhost:8080/user/getCsrf")
    .then(responce => {
      setCsrf(responce.data);
    })
    .catch(error =>{
      console.error(error);
  })
  }, []);
  function handleSubmit(){
    axios.post("http://localhost:8080/login",{
      username,
      password,
      withCredentials: true,
    }, {
      headers: {
        'X-CSRF-TOKEN': csrf, 
      }
    }).then(() =>{
      setIsAuth(true);
    })
  };
  if(isAuth == true){
    return(<UserPanel/>)
  }
  return (
    <>
    <div className='formLogin'>
      <p>Sing in</p>
      <input placeholder='username' onChange={(e) => setUsername(e.target.value)}></input>
      <input placeholder='password' onChange={(e) => setPassword(e.target.value)}></input>
      <button onClick={handleSubmit}>Submit</button>
    </div>
    </>
  )
}

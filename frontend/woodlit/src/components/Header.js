import React from 'react'
import { Link } from 'react-router-dom'

export default function Header() {
  return (
    <>
    <div className='container'>
    <header>
        <div className='header-media'>
          <a href='#'><img src='.\images\instagram.png' alt='none logo'/></a>
          <a href='#'><img src='.\images\facebook.png' alt='none logo'/></a>
        </div>  
        <Link to="/"><img src='.\images\logo.avif'></img></Link>
        <div className='header-navbar'>
        <a href='#'><img src='.\images\search.png' alt='none logo'/></a>
        <a href='#'><img src='.\images\profile.png' alt='none logo'/></a>
        <a href='#'><img src='.\images\cart.png' alt='none logo'/></a>
        </div>
    </header>
    <nav> 
      <Link to="/">Home</Link>
      <Link to="/">Products</Link>
      <Link to="/">Learn</Link>
      <Link to="/">Blog</Link>
      <Link to="/">Contacts</Link>
    </nav>
    </div>
    </>
  )
}

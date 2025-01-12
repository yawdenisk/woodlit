import React from 'react'
import { Link } from 'react-router-dom'
import instagram from '../images/instagram.png';
import facebook from '../images/facebook.png';
import logo from '../images/logo.avif';
import searchIcon from '../images/search.png';
import profileIcon from '../images/profile.png';
import cartIcon from '../images/cart.png';

export default function Header() {
  return (
    <>
    <div className='container'>
    <header>
        <div className='header-media'>
          <a href='/'><img src={instagram} alt='none logo'/></a>
          <a href='/'><img src={facebook} alt='none logo'/></a>
        </div>  
        <Link to="/"><img src={logo} alt='none logo'></img></Link>
        <div className='header-navbar'>
        <a href='/'><img src={searchIcon} alt='none logo'/></a>
        <Link to="/user"><img src={profileIcon} alt='none logo'/></Link>
        <Link to="/cart"><img src={cartIcon} alt='none logo'/></Link>
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

import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

export default function Registration() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [lastName, setLastName] = useState('');
    const [country, setCountry] = useState('');
    const [city, setCity] = useState('');
    const [address, setAddress] = useState('');
    const [postIndex, setPostIndex] = useState('');
    const [errors, setErrors] = useState({});

    const navigate = useNavigate();
    
    const getFieldStyle = (field) => {
      return errors[field] ? { borderColor: 'red' } : {};
    };

    async function handleSubmit(event) {
        event.preventDefault();
        const validationErrors = validateForm();
    
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }
        try {
            await axios.post('http://localhost:8080/user/create', {
                username,
                password,
                email,
                name,
                last_name: lastName,
                country,
                city,
                address,
                post_index: postIndex
            })
            navigate('/login')
        } catch (error) {
            if (error.response) {
                const responseData = error.response.data;
                if (responseData && responseData.includes("email")) {
                    setErrors({ email: error.response.data });
                } else if (responseData && responseData.includes("username")) {
                    setErrors({ username: error.response.data });
                } else {
                    setErrors({ server: "An unknown error occurred" });
                }
            }
        }
    }

    const validateForm = () => {
        const validationErrors = {};

        if (!username.trim()) {
            validationErrors.username = 'Username is required';
        }

        if (password.length < 8 || password.length > 16) {
            validationErrors.password = 'Password must be between 8 and 16 characters';
        }
 
        if (!email.trim() || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            validationErrors.email = 'Enter a valid email';
        }

        if (!name.trim()) {
            validationErrors.name = 'Name is required';
        }

        if (!lastName.trim()) {
            validationErrors.lastName = 'Last name is required';
        }

        if (!country.trim()) {
            validationErrors.country = 'Country is required';
        }

        if (!city.trim()) {
            validationErrors.city = 'City is required';
        }

        if (!address.trim()) {
            validationErrors.address = 'City is required';
        }

        if (!postIndex.trim()) {     
            validationErrors.postIndex = 'Postal code is required';
        }

        return validationErrors;
    };

    return (
        <div className='registration'>
          <p>Sing up</p>
                {Object.keys(errors).length > 0 && (
                    <div className="error">
                        <p>{Object.values(errors)[0]}</p>
                    </div>
                )}
            <form className='' onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="username"
                    placeholder="username"
                    onChange={(e) => setUsername(e.target.value)}
                    style={getFieldStyle('username')}
                />
                <input
                    type="text"
                    name="password"
                    placeholder="password"
                    onChange={(e) => setPassword(e.target.value)}
                    style={getFieldStyle('password')} 
                />
                <input
                    type="text"
                    name="email"
                    placeholder="email"
                    onChange={(e) => setEmail(e.target.value)}
                    style={getFieldStyle('email')} 
                />
                <input
                    type="text"
                    name="name"
                    placeholder="name"
                    onChange={(e) => setName(e.target.value)}
                    style={getFieldStyle('name')}
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="last name"
                    onChange={(e) => setLastName(e.target.value)}
                    style={getFieldStyle('lastName')}  
                />
                <input
                    type="text"
                    name="country"
                    placeholder="country"
                    onChange={(e) => setCountry(e.target.value)}
                    style={getFieldStyle('country')} 
                />
                <input
                    type="text"
                    name="city"
                    placeholder="city"
                    onChange={(e) => setCity(e.target.value)}
                    style={getFieldStyle('city')}  
                />
                <input
                    type="text"
                    name="address"
                    placeholder="address"
                    onChange={(e) => setAddress(e.target.value)}
                    style={getFieldStyle('address')}  
                />
                <input
                    type="text"
                    name="postIndex"
                    placeholder="post index"
                    onChange={(e) => setPostIndex(e.target.value)}
                    style={getFieldStyle('postIndex')}  
                />
                <button type="submit">Submit</button>
                <h6>Already have an account? <Link to="/login">Sign in</Link></h6>
            </form>
        </div>
    );
}

import React, { useState } from 'react';
import { useNavigate} from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';

const LoginPage = () => {
  const BASE_URL = "http://localhost:7426";
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleTogglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(BASE_URL+`/customerlogin/${email}/${password}`);
      const customer = response.data;
      toast.success('Login successful');
      setLoggedIn(true);
      localStorage.setItem('customer', JSON.stringify(customer));
    } catch (error) {
      if (error.response && error.response.data) {
        const message = error.response.data.errorMessage;
        toast.error(`Error: ${message}`);
      } else {
        toast.error('Unknown error occurred during login.');
      }
    }
  };

  if (loggedIn) {
    setTimeout(() => {
      navigate('/');  
    }, 2000); 
  }

  return (
    <body style={{
      backgroundImage: `url('https://images.unsplash.com/photo-1612083111232-29f08821e47a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8cmFpbHxlbnwwfHwwfHx8MA%3D%3D&w=1000&q=80')`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      minHeight: '80vh',
      maxHeight: '85.8vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
    <div className="container d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <div className="card" style={{ width: '500px', height: '400px',background: 'rgba(255, 255, 255, 0.8)'}}>
        <h2 className="card-header">Login</h2>
        <div className="card-body">
        <ToastContainer toastClassName="custom-toast" style={{width:'500px'}}/> {/* Add the ToastContainer component */}
          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email:
              </label>
              <input
                type="email"
                className="form-control"
                id="email"
                value={email}
                onChange={handleEmailChange}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password:
              </label>
              <div className="input-group">
                <input
                  type={showPassword ? 'text' : 'password'}
                  className="form-control"
                  id="password"
                  value={password}
                  onChange={handlePasswordChange}
                  required
                />
                <button
                  type="button"
                  className="btn btn-outline-secondary"
                  onClick={handleTogglePasswordVisibility}
                >
                  {showPassword ? (
                    <i className="bi bi-eye-slash-fill"></i>
                  ) : (
                    <i className="bi bi-eye-fill"></i>
                  )}
                </button>
              </div>
            </div>
            <button type="submit" className="btn btn-primary">
              Login
            </button>
          </form>
          <div className="mt-3">
            Don't have an account? <Link to="/register">Register here</Link>
          </div>
        </div>
      </div>
    </div>
    </body>
  );
};

export default LoginPage;




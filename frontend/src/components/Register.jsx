import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/Toast.css';



const Register = () => {
  const BASE_URL = "http://localhost:7426";
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [contactNo, setContactNo] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleTogglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleAddressChange = (event) => {
    setAddress(event.target.value);
  };

  const handleContactNoChange = (event) => {
    setContactNo(event.target.value);
  };

  const handleRegister = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(BASE_URL + '/addcustomer', {
        password,
        firstName,
        lastName,
        email,
        address,
        contactNo,
      });
      const customer = response.data;
      toast.success('Registration successful');
      setTimeout(() => {
        navigate('/login');
      }, 2000);
      console.log('Customer registered:', customer);
    } catch (error) {
      if (error.response && error.response.data) {
        const errorMessage = error.response.data.errorMessage;
        toast.error(`Error: ${errorMessage}`);
      } else {
        toast.error('Unknown error occurred during registration.');
      }
    }
  };

  return (
    <div style={{
      backgroundImage: `url('https://upload.wikimedia.org/wikipedia/commons/c/cd/Baranagar_Road_railway_station_in_Sealda_to_Dankuni_line_03.jpg')`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      minHeight: '80vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
      <div className="container d-flex justify-content-center align-items-center mt-5" style={{ height: '100vh' }}>
        <div className="card" style={{ width: '500px', background: 'rgba(255, 255, 255, 0.8)' }}>
          {/* <div className="card" style={{ width: '500px'}}> */}
          <h2 className="card-header">Register Page</h2>
          <div className="card-body">
            <ToastContainer toastClassName="custom-toast" style={{ width: '500px' }} /> {/* Add the ToastContainer component */}
            <form onSubmit={handleRegister}>
              <div className="mb-3">
                <label htmlFor="firstName" className="form-label">
                  First Name:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="firstName"
                  value={firstName}
                  onChange={handleFirstNameChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="lastName" className="form-label">
                  Last Name:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="lastName"
                  value={lastName}
                  onChange={handleLastNameChange}
                  required
                />
              </div>
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
                <label htmlFor="address" className="form-label">
                  Address:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="address"
                  value={address}
                  onChange={handleAddressChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="contactNo" className="form-label">
                  Contact No:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="contactNo"
                  value={contactNo}
                  onChange={handleContactNoChange}
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
                Register
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;


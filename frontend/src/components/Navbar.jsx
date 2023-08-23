import React from 'react';
import { Link } from 'react-router-dom';
import { useNavigate} from 'react-router-dom';
import '../css/Navbar.css';
import { ImHome } from 'react-icons/im';
import { FaHistory } from 'react-icons/fa';
import { RiLogoutCircleLine } from 'react-icons/ri';
import { CgLogIn } from 'react-icons/cg';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons';

const Navbar = () => {
    const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('customer');
    localStorage.removeItem('selectedTrain');
    navigate('/');
  };

  const customer = JSON.parse(localStorage.getItem('customer'));

  return (
    <nav className="navbar navbar-expand-lg  fixed-top" style={{backgroundColor: '#c6c7c8'}}>
      <div className="container-fluid">
        <Link className="navbar-brand" style={{ fontSize: 25 }} to="/">
        <img src="https://i.ibb.co/xL6BkHw/img-removebg-preview-1.png" width="180" height="60" class="d-inline-block align-top" alt=""></img>
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            <li className="nav-item" style={{ fontSize: 20}}>
              <Link className="nav-link active" aria-current="page" to="/">
              <ImHome size={22} style={{marginBottom:'6px'}}/> Home
              </Link>
            </li>
            {customer ? (
              <>
                <li className="nav-item" style={{ fontSize: 20 }}>
                  <Link className="nav-link active" aria-current="page" to="/displayallbooking">
                  <FaHistory size={21} style={{marginBottom:'6px'}}/> Reservation History
                  </Link>
                </li>
                <li className="nav-item" style={{ fontSize: 20}}>
                  <button className="btn btn-link nav-link active" onClick={handleLogout}>
                  <RiLogoutCircleLine size={22} style={{marginBottom:'6px'}}/>  Logout
                  </button>
                </li>
                <li className="nav-item" style={{ fontSize: 20}}>
                  <Link className="nav-link active" aria-current="page" to="/" >Hi, {customer.firstName + ' ' + customer.lastName}!</Link>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item" style={{ fontSize: 20 }}>
                  <Link className="nav-link active" aria-current="page" to="/login">
                  <CgLogIn size={22} style={{marginBottom:'6px'}}/> Login
                  </Link>
                </li>
                <li className="nav-item" style={{ fontSize: 20 }}> 
                  <Link className="nav-link active" aria-current="page" to="/register">
                  <FontAwesomeIcon icon={faUserPlus} style={{marginRight:'3px'}} size={22} />Register
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

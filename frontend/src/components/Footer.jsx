import React from 'react';
import { Link } from 'react-router-dom';
import { FaJava, FaReact } from 'react-icons/fa';
import { SiSpring, SiSwagger, SiSpringboot, SiMysql, SiHibernate } from 'react-icons/si';

const Footer = () => {
  return (
    <footer className="shadow" style={{background: '#aeaeaf'}}>
      <div className="mx-auto p-3 d-flex flex-wrap justify-content-between align-items-center" style={{ width: '90%' }}>
        <div className="d-flex align-items-center">
          <Link to="/" className="d-flex align-items-center p-0 text-dark">
            <img alt="logo" src="https://i.ibb.co/Wt2TVT2/logo1.png" width="170px" height="55px" />
          </Link>
        </div>
        <div className="text-center my-3" style={{ fontSize: '18px', fontFamily: 'Playfair Display', fontWeight: 'bold' }}>
          &copy; 2023 Rail Ease. All rights reserved!<br />
          Designed and Developed by: Ayush Jha 😎
        </div>
        <div className="d-flex flex-wrap justify-content-center align-items-center">
          <button className="btn btn-flat btn-dark p-2">
            <FaJava size={24} />
          </button>
          <button className="btn btn-flat btn-dark mx-3 p-2">
            <SiSpring size={24} />
          </button>
          <button className="btn btn-flat btn-dark p-2">
            <SiSpringboot size={24} />
          </button>
          <button className="btn btn-flat btn-dark mx-3 p-2">
            <SiHibernate size={24} />
          </button>
          <button className="btn btn-flat btn-dark p-2">
            <FaReact size={24} />
          </button>
          <button className="btn btn-flat btn-dark mx-3 p-2">
            <SiSwagger size={24} />
          </button>
          <button className="btn btn-flat btn-dark p-2">
            <SiMysql size={24} />
          </button>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

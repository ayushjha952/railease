import React from 'react';
import { Link } from 'react-router-dom';
import { useState,useEffect } from 'react';

const Home = () => {

  const [headingText, setHeadingText] = useState('');
  const fullHeadingText = 'Unlock the Rails: Your Ticket to Seamless Travel Experiences';

  useEffect(() => {
    let currentIndex = 0;
    const typingTimer = setInterval(() => {
      if (currentIndex === fullHeadingText.length) {
        clearInterval(typingTimer);
      } else {
        setHeadingText(fullHeadingText.slice(0, currentIndex + 1));
        currentIndex++;
      }
    }, 40);

    return () => {
      clearInterval(typingTimer);
    };
  }, []);
  return (
    <>
    <div style={{backgroundColor:'#F5EDE5'}}>
      <div className="container mt-5"></div>
      <br />
      <div className="container mt-3">
        <h1 style={{textAlign:'center',fontFamily:'Bree Serif'}}>{headingText}</h1>
      </div>
      <br />
      <div className="container mt-1">
        <div className="row">
          <div className="col">
            <div id="carouselExampleIndicators" className="carousel slide shadow" data-bs-ride="carousel">
              <div className="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" className="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
              </div>
              <div className="carousel-inner">
                <div className="carousel-item active" data-bs-interval="2000">
                  <img src="https://th-i.thgim.com/public/incoming/mehrei/article66778905.ece/alternates/FREE_1200/vande%20bharat%20kerala.jpeg" className="d-block w-100" alt="..." style={{ width: '1080px', height: '633px' }} />
                </div>
                <div className="carousel-item" data-bs-interval="2000">
                  <img src="https://w.forfun.com/fetch/d9/d9acf7600af70619b5fe352bc175f404.jpeg" className="d-block w-100" alt="..." style={{ width: '1080px', height: '633px' }} />
                </div>
                <div className="carousel-item" data-bs-interval="2000">
                  <img src="https://e1.pxfuel.com/desktop-wallpaper/1003/63/desktop-wallpaper-scottish-railway-track-rail-bridge-fence-river-green-mountain-sky-for-pc-3840x2400-13-railway-full.jpg" className="d-block w-100" alt="..." style={{ width: '1080px', height: '633px' }} />
                </div>
              </div>
              <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
              </button>
              <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
              </button>
            </div>
          </div>
          </div>
          </div>
          <div className="container mt-3">
          {/* <div className="col-md-4 mt-2">
            <div className="container"> */}
              <div className="row">
                <div className="col-sm-12 col-md-6 col-lg-4 mt-2 mb-2">
                  <div className="card shadow h-100">
                    <img src="https://i.ibb.co/DQB08Yg/trainno.jpg" className="card-img-top" alt="Train by Train No" style={{height:'200.17px'}}  />
                    <div className="card-body">
                      <h5 className="card-title mt-2" style={{fontFamily:'Playfair Display',fontWeight:'bold'}}>Search Train by Train No</h5>
                      <Link to="/train-by-train-no" className="btn btn-primary">Search</Link>
                    </div>
                  </div>
                </div>
              {/* </div> */}
              {/* <div className="row mt-3"> */}
                <div className="col-sm-12 col-md-6 col-lg-4 mt-2  mb-2">
                  <div className="card shadow h-100">
                    <img src="https://i.ibb.co/tPVycQQ/source.jpg" className="card-img-top" alt="Train by Source and Destination" style={{height:'200.17px'}} />
                    <div className="card-body">
                      <h5 className="card-title mt-2" style={{fontFamily:'Playfair Display',fontWeight:'bold'}}>Search Train by Source and Destination</h5>
                      <Link to="/train-by-source-and-destination" className="btn btn-primary ">Search</Link>
                    </div>
                  </div>
                </div>
              {/* </div> */}
              {/* <div className="row mt-3"> */}
                <div className="col-sm-12 col-md-6 mx-auto col-lg-4 mt-2 mb-2">
                  <div className="card shadow h-100">
                    <img src="https://i.ibb.co/X3RVgg6/name.jpg" className="card-img-top" alt="Train by Train Name" style={{height:'200.17px'}}/>
                    <div className="card-body">
                      <h5 className="card-title mt-2" style={{fontFamily:'Playfair Display',fontWeight:'bold'}}>Search Train by Train Name</h5>
                      <Link to="/train-by-train-name" className="btn btn-primary">Search</Link>
                    </div>
                  </div>
                </div>
              {/* </div> */}
            </div>
          </div>
        </div>
        {/* </div>
        </div> */}
    </>
  );
};

export default Home;

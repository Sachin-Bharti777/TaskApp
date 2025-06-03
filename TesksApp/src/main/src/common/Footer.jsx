const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-content">
            <p>&copy; {new Date().getFullYear()}TaskManger App. All right reserved</p>
            <div className="footer-links">
            <a className="footer-link" href="">Terms of service</a>
             <a className="footer-link" href="">Privacy Policy</a>
              <a className="footer-link" href="">Contact Us</a>

            </div>
            </div>
        </footer>
    )
}

export default Footer;
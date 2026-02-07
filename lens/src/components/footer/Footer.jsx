import "../footer/Footer.css"

export const Footer = () => {
  return (
      <footer className="footer">
      <div className="footer-container">

        {/* Brand */}
        <div className="footer-brand">
          <h2 className="footer-logo">mynxtduty</h2>
          <p className="footer-tagline">
            Helping you find your next step — always.
          </p>
        </div>

        {/* Links */}
        <div className="footer-links">
          <div>
            <h4>Platform</h4>
            <ul>
              <li><a href="/about">About</a></li>
              <li><a href="/community">Community</a></li>
              <li><a href="/mentors">Mentorship</a></li>
              <li><a href="/roadmap">Roadmap</a></li>
            </ul>
          </div>

          <div>
            <h4>Resources</h4>
            <ul>
              <li><a href="/guides">Guides</a></li>
              <li><a href="/faq">FAQs</a></li>
              <li><a href="/blog">Blog</a></li>
            </ul>
          </div>

          <div>
            <h4>Legal</h4>
            <ul>
              <li><a href="/privacy">Privacy Policy</a></li>
              <li><a href="/terms">Terms of Service</a></li>
            </ul>
          </div>
        </div>

      </div>

      {/* Bottom bar */}
      <div className="footer-bottom">
        <p>© {new Date().getFullYear()} mynextduty. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Button = ({ text = "text", onClick }) => {
  return <button onClick={onClick}>{text}</button>;
};

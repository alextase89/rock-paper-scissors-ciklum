import Nav from "react-bootstrap/Nav";
import Button from "react-bootstrap/Button";
import PropTypes from "prop-types";

const GameControls = ({
  selectShapeFunc,
  newRoundFunc,
  resetGameFunc
}) => {

 const shapeSelector = () => {
   return (
        <div className="row .play-selection" >
          <div className="col-4">
            Select your Play:
          </div>
          <div className="col-8">
            <Nav variant="pills" defaultActiveKey="rock">
              <Nav.Item>
                <Nav.Link eventKey="rock" onClick={()=>selectShapeFunc('ROCK')}>ROCK</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="paper" onClick={()=>selectShapeFunc('PAPER')}>PAPER</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="scissor" onClick={()=>selectShapeFunc('SCISSOR')}>SCISSOR</Nav.Link>
              </Nav.Item>
            </Nav>
          </div>
        </div>
    );
  }

  return (
      <div className="row">
        { shapeSelector() }

        <div className="play-controls">
            <Button variant="primary" onClick={ newRoundFunc }>Play Round</Button>
            <Button variant="danger" onClick={ resetGameFunc }>Reset Game</Button>
        </div>
      </div>
  )
}

GameControls.propTypes = {
  'selectShapeFunc': PropTypes.func.isRequired,
  'newRoundFunc': PropTypes.func.isRequired,
  'resetGameFunc': PropTypes.func
}
export default GameControls;
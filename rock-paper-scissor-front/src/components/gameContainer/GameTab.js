import GameControls from "./GameControls";
import RoundsTable from "./RoundsTable";
import PropTypes from 'prop-types';

const GameTab = ({
  selectShapeFunc,
  newRoundFunc,
  resetGameFunc,
  rounds
}) => {


  return (
      <div className="row game-container">
        <div className="col-6">
          <GameControls
              selectShapeFunc={ selectShapeFunc }
              newRoundFunc={ newRoundFunc }
              resetGameFunc={ resetGameFunc }
          />
        </div>
        <div className="col-6">
          <RoundsTable
              rounds={rounds}
          />
        </div>
      </div>
  )
}

GameTab.propTypes = {
  'selectShapeFunc': PropTypes.func.isRequired,
  'newRoundFunc': PropTypes.func.isRequired,
  'resetGameFunc': PropTypes.func.isRequired,
  'rounds': PropTypes.array.isRequired
}

export default GameTab;
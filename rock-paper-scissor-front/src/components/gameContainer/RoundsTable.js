import Table from 'react-bootstrap/Table';
import PropTypes from 'prop-types';

const RoundsTable = ({
  rounds
}) => {

  const humanResultDescription = {
    'PLAYER1_WINS': 'Player1 Wins!',
    'PLAYER2_WINS': 'Player2 Wins!',
    'DRAW': `It's a draw!`
  }
  return (
      <div className= "rounds-table">
        <h5>Rounds counter: {rounds.length}</h5>
        <Table striped bordered hover>
          <thead>
          <tr>
            <th>Player 1 (YOU)</th>
            <th>Player 2 (CPU)</th>
            <th>Round Result</th>
          </tr>
          </thead>
          <tbody>
          {rounds.map(round => {
            return (
                <tr>
                  <td>
                    {round.player1Shape}
                  </td>
                  <td>
                    {round.player2Shape}
                  </td>
                  <td>
                    {humanResultDescription[round.roundResult]}
                  </td>
                </tr>
            );
          })}
          </tbody>
        </Table>
      </div>
  );
}

RoundsTable.propTypes = {
  'rounds': PropTypes.array.isRequired
}

export default RoundsTable;
const jwt = require('jsonwebtoken');
const SECRET = 'STORMBREAKERS';


const extractBearerToken = headerValue => {
    if (typeof headerValue !== 'string') {
       return false;
    }
    const matches = headerValue.match(/(bearer)\s+(\S+)/i);
    return matches && matches[2];
  
  }
  
const checkTokenMiddleware = (req, res, next) => {
    const token = req.headers.authorization && extractBearerToken(req.headers.authorization);

    if(!token){
        return res.status(401).json({message: 'Error. Need a token!'});
    }

    jwt.verify(token,SECRET, (err,decodedToken) => {
        if (err) {
            res.status(401).json({message : 'Error. Bad token'});
        } else {
            return next();
        }
    }
    
    )
} 

module.exports = {checkTokenMiddleware, extractBearerToken};
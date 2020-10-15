describe('MasterService without Angular testing support', () => {
    var hasErrors = false
    const loadtest = require('loadtest');
    const options = {
        url: 'http://localhost:4200',
        maxRequests: 1000,
    };
    loadtest.loadTest(options, function(error, result)
    {
        if (error)
        {
            hasErrors = true
            return console.error('Got an error: %s', error);
            
        }
        
        console.log('Tests run successfully');
        expect(hasErrors).toBe(false);
    });

    
  });
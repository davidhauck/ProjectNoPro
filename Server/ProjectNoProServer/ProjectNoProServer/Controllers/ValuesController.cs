using ProjectNoProServer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ProjectNoProServer.Controllers
{
    public class ValuesController : ApiController
    {
        static string PreviousMessage = "Hello";
        
        public string Get()
        {
            return PreviousMessage;
        }

        public void Post(Message value)
        {
            PreviousMessage = value.Value;
        }
    }
}

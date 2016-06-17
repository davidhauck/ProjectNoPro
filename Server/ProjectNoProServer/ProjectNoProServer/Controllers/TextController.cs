using Microsoft.AspNet.Identity;
using ProjectNoProServer.Entities;
using ProjectNoProServer.Hubs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace ProjectNoProServer.Controllers
{
    public class TextController : ApiControllerWithHub<NotificationHub>
    {
        // GET: api/Text/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Text
        [HostAuthentication(DefaultAuthenticationTypes.ExternalBearer)]
        public void Post([FromBody]TextMessage value)
        {
            string id = null;
            foreach (var claim in (User.Identity as ClaimsIdentity).Claims)
            {
                if (claim.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier")
                    id = claim.Value;
            }

            Hub.Clients.Group(id).TextMessageReceived(value);
        }
    }
}

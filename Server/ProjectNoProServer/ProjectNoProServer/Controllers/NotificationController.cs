using Microsoft.AspNet.Identity;
using Microsoft.AspNet.SignalR.Messaging;
using Microsoft.Owin.Security;
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
    public class NotificationController : ApiControllerWithHub<NotificationHub>
    {
        // GET: api/Notification
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/Notification/5
        public string Get(int id)
        {
            Hub.Clients.All.TestNotification("notification", id);
            return "value";
        }

        // POST: api/Notification
        [HostAuthentication(DefaultAuthenticationTypes.ExternalBearer)]
        public async void Post([FromBody]Entities.Message value)
        {
            string id = null;
            foreach (var claim in (User.Identity as ClaimsIdentity).Claims)
            {
                if (claim.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier")
                    id = claim.Value;
            }

            Hub.Clients.Group(id).TestNotification(value.Title, value.Text);
        }


        private IAuthenticationManager Authentication
        {
            get { return Request.GetOwinContext().Authentication; }
        }

        // PUT: api/Notification/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Notification/5
        public void Delete(int id)
        {
        }
    }
}

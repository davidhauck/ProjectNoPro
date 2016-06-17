using Microsoft.AspNet.SignalR.Messaging;
using ProjectNoProServer.Hubs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
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
        public void Post([FromBody]Entities.Message value)
        {
            Hub.Clients.All.TestNotification("notification", value.Value);
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

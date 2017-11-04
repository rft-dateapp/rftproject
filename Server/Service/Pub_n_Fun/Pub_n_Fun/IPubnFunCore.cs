using System.ServiceModel;
using System.ServiceModel.Web;

namespace Pub_n_Fun
{
    [ServiceContract]
    public interface IPubnFunCore
    {
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/token/{token}", ResponseFormat = WebMessageFormat.Json)]
        string GetSystemDate(string token);
    }
}

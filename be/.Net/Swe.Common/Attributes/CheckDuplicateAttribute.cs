using Swe.Common.Resources;

namespace Swe.Common.Attributes;

// Cau hinh de thuoc tinh nay ap dung len property 
[AttributeUsage(AttributeTargets.Property)]
public class CheckDuplicateAttribute(string errorMessageKey) : Attribute
{
    public string ErrorMessage => ResourcesVN.ResourceManager.GetString(errorMessageKey) ?? errorMessageKey;
}
